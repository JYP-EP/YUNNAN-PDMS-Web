// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: warning.proto

package com.pdms.grpc.domain;

public interface WarningItemReplyOrBuilder extends
    // @@protoc_insertion_point(interface_extends:domain.WarningItemReply)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   *站所名称
   * </pre>
   *
   * <code>string stationName = 1;</code>
   * @return The stationName.
   */
  java.lang.String getStationName();
  /**
   * <pre>
   *站所名称
   * </pre>
   *
   * <code>string stationName = 1;</code>
   * @return The bytes for stationName.
   */
  com.google.protobuf.ByteString
      getStationNameBytes();

  /**
   * <pre>
   *告警分类
   * </pre>
   *
   * <code>string type = 2;</code>
   * @return The type.
   */
  java.lang.String getType();
  /**
   * <pre>
   *告警分类
   * </pre>
   *
   * <code>string type = 2;</code>
   * @return The bytes for type.
   */
  com.google.protobuf.ByteString
      getTypeBytes();

  /**
   * <pre>
   *告警数量
   * </pre>
   *
   * <code>string warningNumber = 3;</code>
   * @return The warningNumber.
   */
  java.lang.String getWarningNumber();
  /**
   * <pre>
   *告警数量
   * </pre>
   *
   * <code>string warningNumber = 3;</code>
   * @return The bytes for warningNumber.
   */
  com.google.protobuf.ByteString
      getWarningNumberBytes();
}