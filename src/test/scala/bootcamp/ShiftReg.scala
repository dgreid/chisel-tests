package bootcamp

import chisel3._
import chisel3.util._
import chisel3.tester._
import chisel3.tester.RawTester.test
import chisel3.iotesters.PeekPokeTester

class ShiftRegTester(c: ShiftReg) extends PeekPokeTester(c) {
  var state = peek(c.init)
  for (i <- 0 until 10) {
    // poke in LSB of i (i % 2)
    poke(c.io.in, ((i % 2) != 0).B)
    // update expected state
    state = ((state * 2) + (i % 2)) & 0xf
    step(1)
    expect(c.io.out, state)
  }
}

object ShiftRegTester extends App {
  iotesters.Driver.execute(Array[String](), () => new ShiftReg()) {
    c => new ShiftRegTester(c)
  }
}
