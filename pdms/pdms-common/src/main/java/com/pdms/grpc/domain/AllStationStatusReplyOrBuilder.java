// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: subStationStatus.proto

package com.pdms.grpc.domain;

public interface AllStationStatusReplyOrBuilder extends
    // @@protoc_insertion_point(interface_extends:domain.AllStationStatusReply)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   *在线率
   * </pre>
   *
   * <code>string onlineRate = 1;</code>
   * @return The onlineRate.
   */
  java.lang.String getOnlineRate();
  /**
   * <pre>
   *在线率
   * </pre>
   *
   * <code>string onlineRate = 1;</code>
   * @return The bytes for onlineRate.
   */
  com.google.protobuf.ByteString
      getOnlineRateBytes();

  /**
   * <pre>
   *MMS通信率
   * </pre>
   *
   * <code>string mmsRate = 2;</code>
   * @return The mmsRate.
   */
  java.lang.String getMmsRate();
  /**
   * <pre>
   *MMS通信率
   * </pre>
   *
   * <code>string mmsRate = 2;</code>
   * @return The bytes for mmsRate.
   */
  com.google.protobuf.ByteString
      getMmsRateBytes();

  /**
   * <pre>
   *GOOSE通信率
   * </pre>
   *
   * <code>string gooseRate = 3;</code>
   * @return The gooseRate.
   */
  java.lang.String getGooseRate();
  /**
   * <pre>
   *GOOSE通信率
   * </pre>
   *
   * <code>string gooseRate = 3;</code>
   * @return The bytes for gooseRate.
   */
  com.google.protobuf.ByteString
      getGooseRateBytes();

  /**
   * <pre>
   *SMV通信率
   * </pre>
   *
   * <code>string smvRate = 4;</code>
   * @return The smvRate.
   */
  java.lang.String getSmvRate();
  /**
   * <pre>
   *SMV通信率
   * </pre>
   *
   * <code>string smvRate = 4;</code>
   * @return The bytes for smvRate.
   */
  com.google.protobuf.ByteString
      getSmvRateBytes();
}