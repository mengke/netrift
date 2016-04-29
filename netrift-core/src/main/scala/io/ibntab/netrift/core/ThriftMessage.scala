package io.ibntab.netrift.core

import io.netty.buffer.ByteBuf

/**
  * Created by ke.meng on 2016/4/29.
  *
  */
case class ThriftMessage(byteBuf: ByteBuf, transportType: ThriftTransportType) {

  var processStartTimeMillis: Long = 0l

  def getMessageFactory =
    new Factory {
      override def apply(messageBuffer: ByteBuf): ThriftMessage = ThriftMessage(messageBuffer, transportType)
    }
}

private[core] trait Factory {
  def apply(messageBuffer: ByteBuf): ThriftMessage
}

sealed abstract class ThriftTransportType(val name: String, val value: Int) {
  // for java compat
  def get(): ThriftTransportType = this
}

object ThriftTransportType {
  case object UNFRAMED extends ThriftTransportType("UNFRAMED", 0)
  case object FRAMED extends ThriftTransportType("FRAMED", 1)
  case object HTTP extends ThriftTransportType("HTTP", 2)
  case object HEADER extends ThriftTransportType("HEADER", 3)
}
