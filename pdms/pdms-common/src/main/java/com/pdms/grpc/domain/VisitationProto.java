// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: visitation.proto

package com.pdms.grpc.domain;

public final class VisitationProto {
  private VisitationProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_domain_VisitationRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_domain_VisitationRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_domain_VisitationReply_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_domain_VisitationReply_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\020visitation.proto\022\006domain\")\n\021Visitation" +
      "Request\022\024\n\014substationId\030\001 \001(\t\")\n\017Visitat" +
      "ionReply\022\026\n\016visitationRate\030\001 \001(\t2]\n\nVisi" +
      "tation\022O\n\027GetVisitationStatistics\022\031.doma" +
      "in.VisitationRequest\032\027.domain.Visitation" +
      "Reply\"\000B)\n\024com.pdms.grpc.domainB\017Visitat" +
      "ionProtoP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_domain_VisitationRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_domain_VisitationRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_domain_VisitationRequest_descriptor,
        new java.lang.String[] { "SubstationId", });
    internal_static_domain_VisitationReply_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_domain_VisitationReply_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_domain_VisitationReply_descriptor,
        new java.lang.String[] { "VisitationRate", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}