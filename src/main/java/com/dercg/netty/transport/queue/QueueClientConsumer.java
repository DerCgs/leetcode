package com.dercg.netty.transport.queue;

import com.dercg.netty.transport.codec.ResultInfo;
import com.dercg.netty.transport.codec.StatusCode;
import com.dercg.netty.transport.module.ModuleClientService;
import com.dercg.netty.transport.module.SyncContext;
import com.dercg.netty.transport.service.ProviderStrategyType;
import com.dercg.netty.transport.service.ServiceEntry;
import com.lmax.disruptor.EventHandler;

public class QueueClientConsumer implements EventHandler<ClientEventInfo> {
    protected ModuleClientService moduleClientService;

    public QueueClientConsumer(ModuleClientService moduleClientService) {
        this.moduleClientService = moduleClientService;
    }

    protected void init() throws Exception {
        moduleClientService.init();
    }

    @Override
    public void onEvent(ClientEventInfo event, long sequence, boolean endOfBatch) throws Exception {
        ResultInfo result = null;
        ServiceEntry firstEntry = null;
        long retryCount = 1;
        for (; ; ) {
            try {
                ServiceEntry entry = moduleClientService.getServiceAddress(event.getServiceName(), event.getStrategyType());
                if (entry == null) {
                    continue;
                }

                if (event.getStrategyType() == ProviderStrategyType.INTSTICKY) {
                    if (retryCount == 1) {
                        firstEntry = entry;
                    } else if (!entry.equals(firstEntry)) {
                        break;
                    }
                }

                if (retryCount > 1) {
                    break;
                }

                result = moduleClientService.onClientSend(event, entry);
                if (result.getErrorCode() > 0 || result.getErrorCode() == StatusCode.CONNECTIONCLOSED) {
                    break;
                }
            } catch (Throwable e) {
                e.printStackTrace();
                result = new ResultInfo();
                result.setErrorCode(StatusCode.EXCEPTION);
            } finally {
                retryCount = retryCount + 1;
            }
        }

        SyncContext userContext = moduleClientService.getSyncContexts(event.getId());
        if (userContext != null) {
            userContext.setResult(result);
        }
    }
}
