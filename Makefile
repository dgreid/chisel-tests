SBT = sbt

all: arbiter shiftreg

test: arbiter_test shiftreg_test

# Arbiter from comb logic
arbiter:
	$(SBT) "runMain bootcamp.ArbiterMain"

arbiter_test:
	$(SBT) "test:runMain bootcamp.ArbiterTester"

# ShiftReg from seq logic
shiftreg:
	$(SBT) "runMain bootcamp.ShiftRegMain"

shiftreg_test:
	$(SBT) "test:runMain bootcamp.ShiftRegTester"

# ShiftRegOpt from seq logic
shiftregopt:
	$(SBT) "runMain bootcamp.ShiftRegOptMain"

shiftregopt_test:
	$(SBT) "test:runMain bootcamp.ShiftRegOptTester"
