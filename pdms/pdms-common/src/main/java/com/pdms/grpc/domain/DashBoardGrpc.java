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
    comments = "Source: dashboard.proto")
public final class DashBoardGrpc {

  private DashBoardGrpc() {}

  public static final String SERVICE_NAME = "domain.DashBoard";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.pdms.grpc.domain.GrpcCommonRequest,
      com.pdms.grpc.domain.GrpcCommonReply> getGetDashBoardGrpcDataMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetDashBoardGrpcData",
      requestType = com.pdms.grpc.domain.GrpcCommonRequest.class,
      responseType = com.pdms.grpc.domain.GrpcCommonReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.pdms.grpc.domain.GrpcCommonRequest,
      com.pdms.grpc.domain.GrpcCommonReply> getGetDashBoardGrpcDataMethod() {
    io.grpc.MethodDescriptor<com.pdms.grpc.domain.GrpcCommonRequest, com.pdms.grpc.domain.GrpcCommonReply> getGetDashBoardGrpcDataMethod;
    if ((getGetDashBoardGrpcDataMethod = DashBoardGrpc.getGetDashBoardGrpcDataMethod) == null) {
      synchronized (DashBoardGrpc.class) {
        if ((getGetDashBoardGrpcDataMethod = DashBoardGrpc.getGetDashBoardGrpcDataMethod) == null) {
          DashBoardGrpc.getGetDashBoardGrpcDataMethod = getGetDashBoardGrpcDataMethod =
              io.grpc.MethodDescriptor.<com.pdms.grpc.domain.GrpcCommonRequest, com.pdms.grpc.domain.GrpcCommonReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetDashBoardGrpcData"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.pdms.grpc.domain.GrpcCommonRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.pdms.grpc.domain.GrpcCommonReply.getDefaultInstance()))
              .setSchemaDescriptor(new DashBoardMethodDescriptorSupplier("GetDashBoardGrpcData"))
              .build();
        }
      }
    }
    return getGetDashBoardGrpcDataMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static DashBoardStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DashBoardStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DashBoardStub>() {
        @java.lang.Override
        public DashBoardStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DashBoardStub(channel, callOptions);
        }
      };
    return DashBoardStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static DashBoardBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DashBoardBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DashBoardBlockingStub>() {
        @java.lang.Override
        public DashBoardBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DashBoardBlockingStub(channel, callOptions);
        }
      };
    return DashBoardBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static DashBoardFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DashBoardFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DashBoardFutureStub>() {
        @java.lang.Override
        public DashBoardFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DashBoardFutureStub(channel, callOptions);
        }
      };
    return DashBoardFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   *gRPC服务定义
   * </pre>
   */
  public static abstract class DashBoardImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *gRPC服务通用方法定义 - Unary
     * </pre>
     */
    public void getDashBoardGrpcData(com.pdms.grpc.domain.GrpcCommonRequest request,
        io.grpc.stub.StreamObserver<com.pdms.grpc.domain.GrpcCommonReply> responseObserver) {
      asyncUnimplementedUnaryCall(getGetDashBoardGrpcDataMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetDashBoardGrpcDataMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.pdms.grpc.domain.GrpcCommonRequest,
                com.pdms.grpc.domain.GrpcCommonReply>(
                  this, METHODID_GET_DASH_BOARD_GRPC_DATA)))
          .build();
    }
  }

  /**
   * <pre>
   *gRPC服务定义
   * </pre>
   */
  public static final class DashBoardStub extends io.grpc.stub.AbstractAsyncStub<DashBoardStub> {
    private DashBoardStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DashBoardStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DashBoardStub(channel, callOptions);
    }

    /**
     * <pre>
     *gRPC服务通用方法定义 - Unary
     * </pre>
     */
    public void getDashBoardGrpcData(com.pdms.grpc.domain.GrpcCommonRequest request,
        io.grpc.stub.StreamObserver<com.pdms.grpc.domain.GrpcCommonReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetDashBoardGrpcDataMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   *gRPC服务定义
   * </pre>
   */
  public static final class DashBoardBlockingStub extends io.grpc.stub.AbstractBlockingStub<DashBoardBlockingStub> {
    private DashBoardBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DashBoardBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DashBoardBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *gRPC服务通用方法定义 - Unary
     * </pre>
     */
    public com.pdms.grpc.domain.GrpcCommonReply getDashBoardGrpcData(com.pdms.grpc.domain.GrpcCommonRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetDashBoardGrpcDataMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   *gRPC服务定义
   * </pre>
   */
  public static final class DashBoardFutureStub extends io.grpc.stub.AbstractFutureStub<DashBoardFutureStub> {
    private DashBoardFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DashBoardFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DashBoardFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *gRPC服务通用方法定义 - Unary
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.pdms.grpc.domain.GrpcCommonReply> getDashBoardGrpcData(
        com.pdms.grpc.domain.GrpcCommonRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetDashBoardGrpcDataMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_DASH_BOARD_GRPC_DATA = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final DashBoardImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(DashBoardImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_DASH_BOARD_GRPC_DATA:
          serviceImpl.getDashBoardGrpcData((com.pdms.grpc.domain.GrpcCommonRequest) request,
              (io.grpc.stub.StreamObserver<com.pdms.grpc.domain.GrpcCommonReply>) responseObserver);
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

  private static abstract class DashBoardBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    DashBoardBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.pdms.grpc.domain.DashBoardProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("DashBoard");
    }
  }

  private static final class DashBoardFileDescriptorSupplier
      extends DashBoardBaseDescriptorSupplier {
    DashBoardFileDescriptorSupplier() {}
  }

  private static final class DashBoardMethodDescriptorSupplier
      extends DashBoardBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    DashBoardMethodDescriptorSupplier(String methodName) {
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
      synchronized (DashBoardGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new DashBoardFileDescriptorSupplier())
              .addMethod(getGetDashBoardGrpcDataMethod())
              .build();
        }
      }
    }
    return result;
  }
}
