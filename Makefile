default: Gomoku

JC = javac
JFLAGS = --module-path "%PATH_TO_FX%" --add-modules=javafx.controls
JVM = java
RM = del

all: default run clean

%: %.java
	@$(JC) $(JFLAGS) State.java Display.java Type.java Gomoku.java

run:
	@$(JVM) $(JFLAGS) Gomoku

clean:
	@$(RM) *.class