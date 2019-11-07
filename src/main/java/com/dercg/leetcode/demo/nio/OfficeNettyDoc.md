### Netty API Reference

* AbstractBootstrap
	* ServerBootStrap
* `Channel`
	* (a network socket or a component which is capable of I/O operations such as read,write,connect,and bind)
	* (A channel provides a user the current state of the channel. eg:is it open?is it connected?)
	* (A channel provides a user the configuration parameters of the channnel. eg:receive buffer size)
	* (A channel provides a user the I/O operations that the channel supports)
	* (A channel provides a user the ChannelPipeline which handles all I/O events and requests associated with the channel) 
	* (All I/O operations in Netty are asynchronous.And you will be returned with a ChannelFuture instance which will notify you when the requested I/O operation has succeeded, failed, or canceled.)
	* `ServerChannel`
		* `ServerScokerChannel`
* `ChannelHandler`
	* (handles an I/O event or intercepts an I/O operation , and forwards it to its next handler in its ChannelPipeline)
	* (A ChannelHandler often needs to store some stateful information. The simplest and recommended approach is to use member variables or use AttributeKeys)
	* (If a ChannelHandler is annotated with the @Sharable annotation, it means you can create an instance of the handler just once and add it to one or more ChannelPipelines multiple times without a race condition.)
	* `ChannelInboundHandler`
		* `ChannelInboundHandlerAdapter`
			* (This implementation just forward the operation to the next ChannelHandler in the ChannelPipeline. Sub-classes may override a method implementation to change this.)
			* (Be aware that messages are not released after the channelRead(ChannelHandlerContext, Object) method returns automatically。`SimpleChannelInboundHandler` can released the received messages automatically.)
			* `SimpleChannelInboundHandler`
				* (ChannelInboundHandlerAdapter which allows to explicit only handle a specific type of messages. For example here is an implementation which only handle String messages.)
				
					```java
					public class StringHandler extends SimpleChannelInboundHandler<String> {
						@Override
						protected void channelRead0(ChannelHandlerContext ctx, String message)
						throws Exception {
							System.out.println(message);
						}
					}
					```
				* (Be aware that depending of the constructor parameters it will release all handled messages by passing them to ReferenceCountUtil.release(Object). In this case you may need to use ReferenceCountUtil.retain(Object) if you pass the object to the next handler in the ChannelPipeline.)
	* `ChannelOutboundHandler`
		* `ChannelOutboundHandlerAdapter`

* `EventLoopGroup`
* `ChannelPipeline`
* `ChannelFuture`
* `ChannelConfig`
* `ChannelPromise`