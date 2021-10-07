package bootcamp

import chisel3._
import chisel3.util._

class FIR(b0: Int, b1: Int, b2: Int, b3: Int) extends Module {
  val io = IO(new Bundle {
    val in = Input(UInt(8.W))
    val out = Output(UInt(8.W))
  })

  val x_neg_one = RegInit(UInt(8.W), 0.U)
  val x_neg_two = RegInit(UInt(8.W), 0.U)
  val x_neg_three = RegInit(UInt(8.W), 0.U)

  x_neg_one := io.in
  x_neg_two := x_neg_one
  x_neg_three := x_neg_two

  io.out := io.in * b0.U + x_neg_one * b1.U + x_neg_two * b2.U + x_neg_three * b3.U
}

object FIRMain extends App {
  println("Generating the shift reg hardware")
  (new chisel3.stage.ChiselStage).emitVerilog(new FIR(1,2,3,4), Array("--target-dir", "generated"))
}

