// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: warning.proto

package com.pdms.grpc.domain;

public final class WarningProto {
  private WarningProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_domain_WarningRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_domain_WarningRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_domain_WarningAndLockReply_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_domain_WarningAndLockReply_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_domain_WarningListRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_domain_WarningListRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_domain_WarningListReply_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_domain_WarningListReply_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_domain_WarningItemReply_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_domain_WarningItemReply_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\rwarning.proto\022\006domain\"&\n\016WarningReques" +
      "t\022\024\n\014substationId\030\001 \001(\t\"Z\n\023WarningAndLoc" +
      "kReply\022\025\n\rwarningNumber\030\001 \001(\t\022\022\n\nlockNum" +
      "ber\030\002 \001(\t\022\030\n\020procStatusNumber\030\003 \001(\t\"\024\n\022W" +
      "arningListRequest\";\n\020WarningListReply\022\'\n" +
      "\005reply\030\001 \003(\0132\030.domain.WarningItemReply\"L" +
      "\n\020WarningItemReply\022\023\n\013stationName\030\001 \001(\t\022" +
      "\014\n\004type\030\002 \001(\t\022\025\n\rwarningNumber\030\003 \001(\t2\254\001\n" +
      "\007Warning\022M\n\024GetWarningStatistics\022\026.domai" +
      "n.WarningRequest\032\033.domain.WarningAndLock" +
      "Reply\"\000\022R\n\030GetSubstationWarningList\022\032.do" +
      "main.WarningListRequest\032\030.domain.Warning" +
      "ListReply\"\000B&\n\024com.pdms.grpc.domainB\014War" +
      "ningProtoP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_domain_WarningRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_domain_WarningRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_domain_WarningRequest_descriptor,
        new java.lang.String[] { "SubstationId", });
    internal_static_domain_WarningAndLockReply_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_domain_WarningAndLockReply_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_domain_WarningAndLockReply_descriptor,
        new java.lang.String[] { "WarningNumber", "LockNumber", "ProcStatusNumber", });
    internal_static_domain_WarningListRequest_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_domain_WarningListRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_domain_WarningListRequest_descriptor,
        new java.lang.String[] { });
    internal_static_domain_WarningListReply_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_domain_WarningListReply_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_domain_WarningListReply_descriptor,
        new java.lang.String[] { "Reply", });
    internal_static_domain_WarningItemReply_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_domain_WarningItemReply_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_domain_WarningItemReply_descriptor,
        new java.lang.String[] { "StationName", "Type", "WarningNumber", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
