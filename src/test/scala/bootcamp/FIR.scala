package bootcamp

import chisel3._
import chisel3.util._
import chisel3.tester._
import chisel3.tester.RawTester.test
import chisel3.iotesters.PeekPokeTester

class FIRTester(c: FIR) extends PeekPokeTester(c) {
      poke(c.io.in, 0.U)
      expect(c.io.out, 0.U)
      step(1)
      poke(c.io.in, 4.U)
      expect(c.io.out, 0.U)
      step(1)
      poke(c.io.in, 5.U)
      expect(c.io.out, 0.U)
      step(1)
      poke(c.io.in, 2.U)
      expect(c.io.out, 0.U)
}

class FIRTester1(c: FIR) extends PeekPokeTester(c) {
    poke(c.io.in, 1.U)
    expect(c.io.out, 1.U)  // 1, 0, 0, 0
    step(1)
    poke(c.io.in, 4.U)
    expect(c.io.out, 5.U)  // 4, 1, 0, 0
    step(1)
    poke(c.io.in, 3.U)
    expect(c.io.out, 8.U)  // 3, 4, 1, 0
    step(1)
    poke(c.io.in, 2.U)
    expect(c.io.out, 10.U)  // 2, 3, 4, 1
    step(1)
    poke(c.io.in, 7.U)
    expect(c.io.out, 16.U)  // 7, 2, 3, 4
    step(1)
    poke(c.io.in, 0.U)
    expect(c.io.out, 12.U)  // 0, 7, 2, 3
}

class FIRTesterInc(c: FIR) extends PeekPokeTester(c) {
    poke(c.io.in, 1.U)
    expect(c.io.out, 1.U)  // 1*1, 0*2, 0*3, 0*4
    step(1)
    poke(c.io.in, 4.U)
    expect(c.io.out, 6.U)  // 4*1, 1*2, 0*3, 0*4
    step(1)
    poke(c.io.in, 3.U)
    expect(c.io.out, 14.U)  // 3*1, 4*2, 1*3, 0*4
    step(1)
    poke(c.io.in, 2.U)
    expect(c.io.out, 24.U)  // 2*1, 3*2, 4*3, 1*4
    step(1)
    poke(c.io.in, 7.U)
    expect(c.io.out, 36.U)  // 7*1, 2*2, 3*3, 4*4
    step(1)
    poke(c.io.in, 0.U)
    expect(c.io.out, 32.U)  // 0*1, 7*2, 2*3, 3*4
}

object FIRTester extends App {
  iotesters.Driver.execute(Array[String](), () => new FIR(0,0,0,0)) {
      c => new FIRTester(c)
    }
  iotesters.Driver.execute(Array[String](), () => new FIR(1,1,1,1)) {
      c => new FIRTester1(c)
    }
  iotesters.Driver.execute(Array[String](), () => new FIR(1,2,3,4)) {
      c => new FIRTesterInc(c)
    }
}

