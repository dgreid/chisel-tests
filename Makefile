SBT = sbt

all: arbiter

test: arbiter_test

# Arbiter from comb logic
arbiter:
	$(SBT) "runMain bootcamp.ArbiterMain"

arbiter_test:
	$(SBT) "test:runMain bootcamp.ArbiterTester"
