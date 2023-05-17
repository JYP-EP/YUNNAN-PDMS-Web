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
    comments = "Source: subStationStatus.proto")
public final class SubStationStatusGrpc {

  private SubStationStatusGrpc() {}

  public static final String SERVICE_NAME = "domain.SubStationStatus";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.pdms.grpc.domain.SubStationStatusRequest,
      com.pdms.grpc.domain.SubStationStatusReply> getGetStatusStatisticsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetStatusStatistics",
      requestType = com.pdms.grpc.domain.SubStationStatusRequest.class,
      responseType = com.pdms.grpc.domain.SubStationStatusReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.pdms.grpc.domain.SubStationStatusRequest,
      com.pdms.grpc.domain.SubStationStatusReply> getGetStatusStatisticsMethod() {
    io.grpc.MethodDescriptor<com.pdms.grpc.domain.SubStationStatusRequest, com.pdms.grpc.domain.SubStationStatusReply> getGetStatusStatisticsMethod;
    if ((getGetStatusStatisticsMethod = SubStationStatusGrpc.getGetStatusStatisticsMethod) == null) {
      synchronized (SubStationStatusGrpc.class) {
        if ((getGetStatusStatisticsMethod = SubStationStatusGrpc.getGetStatusStatisticsMethod) == null) {
          SubStationStatusGrpc.getGetStatusStatisticsMethod = getGetStatusStatisticsMethod =
              io.grpc.MethodDescriptor.<com.pdms.grpc.domain.SubStationStatusRequest, com.pdms.grpc.domain.SubStationStatusReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetStatusStatistics"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.pdms.grpc.domain.SubStationStatusRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.pdms.grpc.domain.SubStationStatusReply.getDefaultInstance()))
              .setSchemaDescriptor(new SubStationStatusMethodDescriptorSupplier("GetStatusStatistics"))
              .build();
        }
      }
    }
    return getGetStatusStatisticsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.pdms.grpc.domain.AllStationStatusRequest,
      com.pdms.grpc.domain.AllStationStatusReply> getGetAllStationStatusRequestMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetAllStationStatusRequest",
      requestType = com.pdms.grpc.domain.AllStationStatusRequest.class,
      responseType = com.pdms.grpc.domain.AllStationStatusReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.pdms.grpc.domain.AllStationStatusRequest,
      com.pdms.grpc.domain.AllStationStatusReply> getGetAllStationStatusRequestMethod() {
    io.grpc.MethodDescriptor<com.pdms.grpc.domain.AllStationStatusRequest, com.pdms.grpc.domain.AllStationStatusReply> getGetAllStationStatusRequestMethod;
    if ((getGetAllStationStatusRequestMethod = SubStationStatusGrpc.getGetAllStationStatusRequestMethod) == null) {
      synchronized (SubStationStatusGrpc.class) {
        if ((getGetAllStationStatusRequestMethod = SubStationStatusGrpc.getGetAllStationStatusRequestMethod) == null) {
          SubStationStatusGrpc.getGetAllStationStatusRequestMethod = getGetAllStationStatusRequestMethod =
              io.grpc.MethodDescriptor.<com.pdms.grpc.domain.AllStationStatusRequest, com.pdms.grpc.domain.AllStationStatusReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetAllStationStatusRequest"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.pdms.grpc.domain.AllStationStatusRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.pdms.grpc.domain.AllStationStatusReply.getDefaultInstance()))
              .setSchemaDescriptor(new SubStationStatusMethodDescriptorSupplier("GetAllStationStatusRequest"))
              .build();
        }
      }
    }
    return getGetAllStationStatusRequestMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SubStationStatusStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SubStationStatusStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SubStationStatusStub>() {
        @java.lang.Override
        public SubStationStatusStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SubStationStatusStub(channel, callOptions);
        }
      };
    return SubStationStatusStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SubStationStatusBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SubStationStatusBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SubStationStatusBlockingStub>() {
        @java.lang.Override
        public SubStationStatusBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SubStationStatusBlockingStub(channel, callOptions);
        }
      };
    return SubStationStatusBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SubStationStatusFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SubStationStatusFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SubStationStatusFutureStub>() {
        @java.lang.Override
        public SubStationStatusFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SubStationStatusFutureStub(channel, callOptions);
        }
      };
    return SubStationStatusFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   *gRPC服务定义
   * </pre>
   */
  public static abstract class SubStationStatusImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *gRPC服务方法定义 - Unary
     * </pre>
     */
    public void getStatusStatistics(com.pdms.grpc.domain.SubStationStatusRequest request,
        io.grpc.stub.StreamObserver<com.pdms.grpc.domain.SubStationStatusReply> responseObserver) {
      asyncUnimplementedUnaryCall(getGetStatusStatisticsMethod(), responseObserver);
    }

    /**
     */
    public void getAllStationStatusRequest(com.pdms.grpc.domain.AllStationStatusRequest request,
        io.grpc.stub.StreamObserver<com.pdms.grpc.domain.AllStationStatusReply> responseObserver) {
      asyncUnimplementedUnaryCall(getGetAllStationStatusRequestMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetStatusStatisticsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.pdms.grpc.domain.SubStationStatusRequest,
                com.pdms.grpc.domain.SubStationStatusReply>(
                  this, METHODID_GET_STATUS_STATISTICS)))
          .addMethod(
            getGetAllStationStatusRequestMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.pdms.grpc.domain.AllStationStatusRequest,
                com.pdms.grpc.domain.AllStationStatusReply>(
                  this, METHODID_GET_ALL_STATION_STATUS_REQUEST)))
          .build();
    }
  }

  /**
   * <pre>
   *gRPC服务定义
   * </pre>
   */
  public static final class SubStationStatusStub extends io.grpc.stub.AbstractAsyncStub<SubStationStatusStub> {
    private SubStationStatusStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SubStationStatusStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SubStationStatusStub(channel, callOptions);
    }

    /**
     * <pre>
     *gRPC服务方法定义 - Unary
     * </pre>
     */
    public void getStatusStatistics(com.pdms.grpc.domain.SubStationStatusRequest request,
        io.grpc.stub.StreamObserver<com.pdms.grpc.domain.SubStationStatusReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetStatusStatisticsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getAllStationStatusRequest(com.pdms.grpc.domain.AllStationStatusRequest request,
        io.grpc.stub.StreamObserver<com.pdms.grpc.domain.AllStationStatusReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetAllStationStatusRequestMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   *gRPC服务定义
   * </pre>
   */
  public static final class SubStationStatusBlockingStub extends io.grpc.stub.AbstractBlockingStub<SubStationStatusBlockingStub> {
    private SubStationStatusBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SubStationStatusBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SubStationStatusBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *gRPC服务方法定义 - Unary
     * </pre>
     */
    public com.pdms.grpc.domain.SubStationStatusReply getStatusStatistics(com.pdms.grpc.domain.SubStationStatusRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetStatusStatisticsMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.pdms.grpc.domain.AllStationStatusReply getAllStationStatusRequest(com.pdms.grpc.domain.AllStationStatusRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetAllStationStatusRequestMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   *gRPC服务定义
   * </pre>
   */
  public static final class SubStationStatusFutureStub extends io.grpc.stub.AbstractFutureStub<SubStationStatusFutureStub> {
    private SubStationStatusFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SubStationStatusFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SubStationStatusFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *gRPC服务方法定义 - Unary
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.pdms.grpc.domain.SubStationStatusReply> getStatusStatistics(
        com.pdms.grpc.domain.SubStationStatusRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetStatusStatisticsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.pdms.grpc.domain.AllStationStatusReply> getAllStationStatusRequest(
        com.pdms.grpc.domain.AllStationStatusRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetAllStationStatusRequestMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_STATUS_STATISTICS = 0;
  private static final int METHODID_GET_ALL_STATION_STATUS_REQUEST = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SubStationStatusImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SubStationStatusImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_STATUS_STATISTICS:
          serviceImpl.getStatusStatistics((com.pdms.grpc.domain.SubStationStatusRequest) request,
              (io.grpc.stub.StreamObserver<com.pdms.grpc.domain.SubStationStatusReply>) responseObserver);
          break;
        case METHODID_GET_ALL_STATION_STATUS_REQUEST:
          serviceImpl.getAllStationStatusRequest((com.pdms.grpc.domain.AllStationStatusRequest) request,
              (io.grpc.stub.StreamObserver<com.pdms.grpc.domain.AllStationStatusReply>) responseObserver);
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

  private static abstract class SubStationStatusBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SubStationStatusBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.pdms.grpc.domain.SubStationStatusProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SubStationStatus");
    }
  }

  private static final class SubStationStatusFileDescriptorSupplier
      extends SubStationStatusBaseDescriptorSupplier {
    SubStationStatusFileDescriptorSupplier() {}
  }

  private static final class SubStationStatusMethodDescriptorSupplier
      extends SubStationStatusBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    SubStationStatusMethodDescriptorSupplier(String methodName) {
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
      synchronized (SubStationStatusGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SubStationStatusFileDescriptorSupplier())
              .addMethod(getGetStatusStatisticsMethod())
              .addMethod(getGetAllStationStatusRequestMethod())
              .build();
        }
      }
    }
    return result;
  }
}
