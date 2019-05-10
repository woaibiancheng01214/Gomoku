default: Oxo

JC = javac
JFLAGS = --module-path "%PATH_TO_FX%" --add-modules=javafx.controls --add-modules=javafx.swing 
JVM = java
RM = del

all: run clean

%: %.java
	@$(JC) $(JFLAGS) State.java Display.java $@.java Type.java CheckInfo.java
	@$(JVM) $(JFLAGS) Oxo

border: BorderPaneExample.java
	@$(JC) $(JFLAGS) State.java Display.java Type.java CheckInfo.java BorderPaneExample.java
	@$(JVM) $(JFLAGS) BorderPaneExample
run:
	
	@$(JVM) $(JFLAGS) Oxo

clean:
	@$(RM) *.class