package bootcamp

import chisel3._
import chisel3.util._

class ShiftReg(val init: UInt = 1.U) extends Module {
  val io = IO(new Bundle {
    val in  = Input(Bool())
    val out = Output(UInt(4.W))
  })

  val state = RegInit(UInt(4.W), init)

  io.out := state

  state := state << 1 | io.in

}

object ShiftRegMain extends App {
  println("Generating the shift reg hardware")
  (new chisel3.stage.ChiselStage).emitVerilog(new ShiftReg(), Array("--target-dir", "generated"))
}
