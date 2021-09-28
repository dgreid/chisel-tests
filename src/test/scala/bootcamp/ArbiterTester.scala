package bootcamp

import chisel3._
import chisel3.util._
import chisel3.tester._
import chisel3.tester.RawTester.test
import chisel3.iotesters.PeekPokeTester

class ArbiterTester(c: Arbiter) extends PeekPokeTester(c) {
  import scala.util.Random
  val data = Random.nextInt(65536)
  poke(c.io.fifo_data,data.U)
  
  for (i <- 0 until 8) {
    poke(c.io.fifo_valid,(((i >> 0) % 2) != 0).B)
    poke(c.io.pe0_ready,(((i >> 1) % 2) != 0).B)
    poke(c.io.pe1_ready,(((i >> 2) % 2) != 0).B)

    expect(c.io.fifo_ready,(i > 1).B)
    expect(c.io.pe0_valid,(i == 3 || i == 7).B)
    expect(c.io.pe1_valid,(i == 5).B)
    
    if (i == 3 || i ==7) {
      expect(c.io.pe0_data,(data).U)
    } else if (i == 5) {
      expect(c.io.pe1_data,(data).U)
    }
  }
}

object ArbiterTester extends App {
  iotesters.Driver.execute(Array[String](), () => new Arbiter()) {
    c => new ArbiterTester(c)
  }
}
