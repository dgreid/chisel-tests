package bootcamp

import chisel3._
import chisel3.util._

class ShiftRegOpt(val n: Int, val init: BigInt = 1) extends Module {
  val io = IO(new Bundle {
    val in  = Input(Bool())
    val en  = Input(Bool())
    val out = Output(UInt(n.W))
  })

  val state = RegInit(init.U(n.W))

  when(io.en) {
    state := state << 1 | io.in
  }

  io.out := state
}

object ShiftRegOptMain extends App {
  println("Generating the shift reg hardware")
  (new chisel3.stage.ChiselStage).emitVerilog(new ShiftRegOpt(4), Array("--target-dir", "generated"))
}
