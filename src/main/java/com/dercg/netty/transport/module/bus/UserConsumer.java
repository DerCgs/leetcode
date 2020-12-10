package com.dercg.netty.transport.module.bus;

import com.dercg.netty.transport.module.bus.UserService;
import com.dercg.netty.transport.queue.QueueServerConsumer;

public class UserConsumer extends QueueServerConsumer {

	public UserConsumer(UserService userService) throws Exception {
		super(userService);
	}
}
