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
    comments = "Source: visitation.proto")
public final class VisitationGrpc {

  private VisitationGrpc() {}

  public static final String SERVICE_NAME = "domain.Visitation";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.pdms.grpc.domain.VisitationRequest,
      com.pdms.grpc.domain.VisitationReply> getGetVisitationStatisticsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetVisitationStatistics",
      requestType = com.pdms.grpc.domain.VisitationRequest.class,
      responseType = com.pdms.grpc.domain.VisitationReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.pdms.grpc.domain.VisitationRequest,
      com.pdms.grpc.domain.VisitationReply> getGetVisitationStatisticsMethod() {
    io.grpc.MethodDescriptor<com.pdms.grpc.domain.VisitationRequest, com.pdms.grpc.domain.VisitationReply> getGetVisitationStatisticsMethod;
    if ((getGetVisitationStatisticsMethod = VisitationGrpc.getGetVisitationStatisticsMethod) == null) {
      synchronized (VisitationGrpc.class) {
        if ((getGetVisitationStatisticsMethod = VisitationGrpc.getGetVisitationStatisticsMethod) == null) {
          VisitationGrpc.getGetVisitationStatisticsMethod = getGetVisitationStatisticsMethod =
              io.grpc.MethodDescriptor.<com.pdms.grpc.domain.VisitationRequest, com.pdms.grpc.domain.VisitationReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetVisitationStatistics"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.pdms.grpc.domain.VisitationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.pdms.grpc.domain.VisitationReply.getDefaultInstance()))
              .setSchemaDescriptor(new VisitationMethodDescriptorSupplier("GetVisitationStatistics"))
              .build();
        }
      }
    }
    return getGetVisitationStatisticsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static VisitationStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<VisitationStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<VisitationStub>() {
        @java.lang.Override
        public VisitationStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new VisitationStub(channel, callOptions);
        }
      };
    return VisitationStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static VisitationBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<VisitationBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<VisitationBlockingStub>() {
        @java.lang.Override
        public VisitationBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new VisitationBlockingStub(channel, callOptions);
        }
      };
    return VisitationBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static VisitationFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<VisitationFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<VisitationFutureStub>() {
        @java.lang.Override
        public VisitationFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new VisitationFutureStub(channel, callOptions);
        }
      };
    return VisitationFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   *gRPC服务定义
   * </pre>
   */
  public static abstract class VisitationImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *gRPC服务方法定义 - Unary
     * </pre>
     */
    public void getVisitationStatistics(com.pdms.grpc.domain.VisitationRequest request,
        io.grpc.stub.StreamObserver<com.pdms.grpc.domain.VisitationReply> responseObserver) {
      asyncUnimplementedUnaryCall(getGetVisitationStatisticsMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetVisitationStatisticsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.pdms.grpc.domain.VisitationRequest,
                com.pdms.grpc.domain.VisitationReply>(
                  this, METHODID_GET_VISITATION_STATISTICS)))
          .build();
    }
  }

  /**
   * <pre>
   *gRPC服务定义
   * </pre>
   */
  public static final class VisitationStub extends io.grpc.stub.AbstractAsyncStub<VisitationStub> {
    private VisitationStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected VisitationStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new VisitationStub(channel, callOptions);
    }

    /**
     * <pre>
     *gRPC服务方法定义 - Unary
     * </pre>
     */
    public void getVisitationStatistics(com.pdms.grpc.domain.VisitationRequest request,
        io.grpc.stub.StreamObserver<com.pdms.grpc.domain.VisitationReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetVisitationStatisticsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   *gRPC服务定义
   * </pre>
   */
  public static final class VisitationBlockingStub extends io.grpc.stub.AbstractBlockingStub<VisitationBlockingStub> {
    private VisitationBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected VisitationBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new VisitationBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *gRPC服务方法定义 - Unary
     * </pre>
     */
    public com.pdms.grpc.domain.VisitationReply getVisitationStatistics(com.pdms.grpc.domain.VisitationRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetVisitationStatisticsMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   *gRPC服务定义
   * </pre>
   */
  public static final class VisitationFutureStub extends io.grpc.stub.AbstractFutureStub<VisitationFutureStub> {
    private VisitationFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected VisitationFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new VisitationFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *gRPC服务方法定义 - Unary
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.pdms.grpc.domain.VisitationReply> getVisitationStatistics(
        com.pdms.grpc.domain.VisitationRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetVisitationStatisticsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_VISITATION_STATISTICS = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final VisitationImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(VisitationImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_VISITATION_STATISTICS:
          serviceImpl.getVisitationStatistics((com.pdms.grpc.domain.VisitationRequest) request,
              (io.grpc.stub.StreamObserver<com.pdms.grpc.domain.VisitationReply>) responseObserver);
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

  private static abstract class VisitationBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    VisitationBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.pdms.grpc.domain.VisitationProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Visitation");
    }
  }

  private static final class VisitationFileDescriptorSupplier
      extends VisitationBaseDescriptorSupplier {
    VisitationFileDescriptorSupplier() {}
  }

  private static final class VisitationMethodDescriptorSupplier
      extends VisitationBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    VisitationMethodDescriptorSupplier(String methodName) {
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
      synchronized (VisitationGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new VisitationFileDescriptorSupplier())
              .addMethod(getGetVisitationStatisticsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
