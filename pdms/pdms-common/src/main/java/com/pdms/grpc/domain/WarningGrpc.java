package com.pdms.grpc.domain;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 * <pre>
 *gRPC服务定义
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.34.1)",
    comments = "Source: warning.proto")
public final class WarningGrpc {

  private WarningGrpc() {}

  public static final String SERVICE_NAME = "domain.Warning";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.pdms.grpc.domain.WarningRequest,
      com.pdms.grpc.domain.WarningAndLockReply> getGetWarningStatisticsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetWarningStatistics",
      requestType = com.pdms.grpc.domain.WarningRequest.class,
      responseType = com.pdms.grpc.domain.WarningAndLockReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.pdms.grpc.domain.WarningRequest,
      com.pdms.grpc.domain.WarningAndLockReply> getGetWarningStatisticsMethod() {
    io.grpc.MethodDescriptor<com.pdms.grpc.domain.WarningRequest, com.pdms.grpc.domain.WarningAndLockReply> getGetWarningStatisticsMethod;
    if ((getGetWarningStatisticsMethod = WarningGrpc.getGetWarningStatisticsMethod) == null) {
      synchronized (WarningGrpc.class) {
        if ((getGetWarningStatisticsMethod = WarningGrpc.getGetWarningStatisticsMethod) == null) {
          WarningGrpc.getGetWarningStatisticsMethod = getGetWarningStatisticsMethod =
              io.grpc.MethodDescriptor.<com.pdms.grpc.domain.WarningRequest, com.pdms.grpc.domain.WarningAndLockReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetWarningStatistics"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.pdms.grpc.domain.WarningRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.pdms.grpc.domain.WarningAndLockReply.getDefaultInstance()))
              .setSchemaDescriptor(new WarningMethodDescriptorSupplier("GetWarningStatistics"))
              .build();
        }
      }
    }
    return getGetWarningStatisticsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.pdms.grpc.domain.WarningListRequest,
      com.pdms.grpc.domain.WarningListReply> getGetSubstationWarningListMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetSubstationWarningList",
      requestType = com.pdms.grpc.domain.WarningListRequest.class,
      responseType = com.pdms.grpc.domain.WarningListReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.pdms.grpc.domain.WarningListRequest,
      com.pdms.grpc.domain.WarningListReply> getGetSubstationWarningListMethod() {
    io.grpc.MethodDescriptor<com.pdms.grpc.domain.WarningListRequest, com.pdms.grpc.domain.WarningListReply> getGetSubstationWarningListMethod;
    if ((getGetSubstationWarningListMethod = WarningGrpc.getGetSubstationWarningListMethod) == null) {
      synchronized (WarningGrpc.class) {
        if ((getGetSubstationWarningListMethod = WarningGrpc.getGetSubstationWarningListMethod) == null) {
          WarningGrpc.getGetSubstationWarningListMethod = getGetSubstationWarningListMethod =
              io.grpc.MethodDescriptor.<com.pdms.grpc.domain.WarningListRequest, com.pdms.grpc.domain.WarningListReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetSubstationWarningList"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.pdms.grpc.domain.WarningListRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.pdms.grpc.domain.WarningListReply.getDefaultInstance()))
              .setSchemaDescriptor(new WarningMethodDescriptorSupplier("GetSubstationWarningList"))
              .build();
        }
      }
    }
    return getGetSubstationWarningListMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static WarningStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<WarningStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<WarningStub>() {
        @java.lang.Override
        public WarningStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new WarningStub(channel, callOptions);
        }
      };
    return WarningStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static WarningBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<WarningBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<WarningBlockingStub>() {
        @java.lang.Override
        public WarningBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new WarningBlockingStub(channel, callOptions);
        }
      };
    return WarningBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static WarningFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<WarningFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<WarningFutureStub>() {
        @java.lang.Override
        public WarningFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new WarningFutureStub(channel, callOptions);
        }
      };
    return WarningFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   *gRPC服务定义
   * </pre>
   */
  public static abstract class WarningImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *gRPC服务方法定义 - Unary
     * </pre>
     */
    public void getWarningStatistics(com.pdms.grpc.domain.WarningRequest request,
        io.grpc.stub.StreamObserver<com.pdms.grpc.domain.WarningAndLockReply> responseObserver) {
      asyncUnimplementedUnaryCall(getGetWarningStatisticsMethod(), responseObserver);
    }

    /**
     * <pre>
     *gRPC服务方法定义 - Unary,获取
     * </pre>
     */
    public void getSubstationWarningList(com.pdms.grpc.domain.WarningListRequest request,
        io.grpc.stub.StreamObserver<com.pdms.grpc.domain.WarningListReply> responseObserver) {
      asyncUnimplementedUnaryCall(getGetSubstationWarningListMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetWarningStatisticsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.pdms.grpc.domain.WarningRequest,
                com.pdms.grpc.domain.WarningAndLockReply>(
                  this, METHODID_GET_WARNING_STATISTICS)))
          .addMethod(
            getGetSubstationWarningListMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.pdms.grpc.domain.WarningListRequest,
                com.pdms.grpc.domain.WarningListReply>(
                  this, METHODID_GET_SUBSTATION_WARNING_LIST)))
          .build();
    }
  }

  /**
   * <pre>
   *gRPC服务定义
   * </pre>
   */
  public static final class WarningStub extends io.grpc.stub.AbstractAsyncStub<WarningStub> {
    private WarningStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WarningStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new WarningStub(channel, callOptions);
    }

    /**
     * <pre>
     *gRPC服务方法定义 - Unary
     * </pre>
     */
    public void getWarningStatistics(com.pdms.grpc.domain.WarningRequest request,
        io.grpc.stub.StreamObserver<com.pdms.grpc.domain.WarningAndLockReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetWarningStatisticsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *gRPC服务方法定义 - Unary,获取
     * </pre>
     */
    public void getSubstationWarningList(com.pdms.grpc.domain.WarningListRequest request,
        io.grpc.stub.StreamObserver<com.pdms.grpc.domain.WarningListReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetSubstationWarningListMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   *gRPC服务定义
   * </pre>
   */
  public static final class WarningBlockingStub extends io.grpc.stub.AbstractBlockingStub<WarningBlockingStub> {
    private WarningBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WarningBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new WarningBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *gRPC服务方法定义 - Unary
     * </pre>
     */
    public com.pdms.grpc.domain.WarningAndLockReply getWarningStatistics(com.pdms.grpc.domain.WarningRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetWarningStatisticsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     *gRPC服务方法定义 - Unary,获取
     * </pre>
     */
    public com.pdms.grpc.domain.WarningListReply getSubstationWarningList(com.pdms.grpc.domain.WarningListRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetSubstationWarningListMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   *gRPC服务定义
   * </pre>
   */
  public static final class WarningFutureStub extends io.grpc.stub.AbstractFutureStub<WarningFutureStub> {
    private WarningFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WarningFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new WarningFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *gRPC服务方法定义 - Unary
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.pdms.grpc.domain.WarningAndLockReply> getWarningStatistics(
        com.pdms.grpc.domain.WarningRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetWarningStatisticsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     *gRPC服务方法定义 - Unary,获取
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.pdms.grpc.domain.WarningListReply> getSubstationWarningList(
        com.pdms.grpc.domain.WarningListRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetSubstationWarningListMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_WARNING_STATISTICS = 0;
  private static final int METHODID_GET_SUBSTATION_WARNING_LIST = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final WarningImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(WarningImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_WARNING_STATISTICS:
          serviceImpl.getWarningStatistics((com.pdms.grpc.domain.WarningRequest) request,
              (io.grpc.stub.StreamObserver<com.pdms.grpc.domain.WarningAndLockReply>) responseObserver);
          break;
        case METHODID_GET_SUBSTATION_WARNING_LIST:
          serviceImpl.getSubstationWarningList((com.pdms.grpc.domain.WarningListRequest) request,
              (io.grpc.stub.StreamObserver<com.pdms.grpc.domain.WarningListReply>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class WarningBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    WarningBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.pdms.grpc.domain.WarningProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Warning");
    }
  }

  private static final class WarningFileDescriptorSupplier
      extends WarningBaseDescriptorSupplier {
    WarningFileDescriptorSupplier() {}
  }

  private static final class WarningMethodDescriptorSupplier
      extends WarningBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    WarningMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (WarningGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new WarningFileDescriptorSupplier())
              .addMethod(getGetWarningStatisticsMethod())
              .addMethod(getGetSubstationWarningListMethod())
              .build();
        }
      }
    }
    return result;
  }
}
