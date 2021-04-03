JCC = javac
JFLAGS = -g

default: Card.class ComputerPlayer.class Guess.class HumanPlayer.class IPlayer.class Main.class Model.class Player.class Type.class
Card.class: Card.java
		$(JCC) $(JFLAGS) Card.java
ComputerPlayer.class: ComputerPlayer.java
		$(JCC) $(JFLAGS) ComputerPlayer.java
Guess.class: Guess.java
		$(JCC) $(JFLAGS) Guess.java
HumanPlayer.class: HumanPlayer.java
		$(JCC) $(JFLAGS) HumanPlayer.java
IPlayer.class: IPlayer.java
		$(JCC) $(JFLAGS) IPlayer.java
Main.class: Main.java
		$(JCC) $(JFLAGS) Main.java
Model.class: Model.java
		$(JCC) $(JFLAGS) Model.java
Player.class: Player.java
		$(JCC) $(JFLAGS) Player.java
Type.class: Type.java
		$(JCC) $(JFLAGS) Type.java
clean:
		$(RM) *.class