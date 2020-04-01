# My Personal Project
#####By: Braeden Norman 
Date Started: 2020/02/05

## Maze Search Game Description

This game will consist of a normally drawn maze that you must navigate through to the end. The maze will not be 
fully visable. Your person or character will only be able to around him in a *Fog of War* type vision. You will have
go through the maze dogging traps and find items that are required to exit.

This application will be designed to be fun to play. Someone who is bored and needs something to do might download this
game, give it a try, and hopefully have some fun. 

This project interest as I have been interest in programming a mini game like the one described above. I, like many
others, love to play video games and designing a mini version of one will be very interesting to me.


Things this game will hopefully include:
- Items to find and maybe equip
- Traps to set off and dodge
- *Fog of War* vision in which you can only see what the person should be able to see
- A maze to go through


## **User Stories**
### Phase 1
- As a user, I want a map that generates a  random list of keys that I can add to
- As a user, I want to be able to move my character around with *wasd* keys
- As a user, I want to be able to pick these items up if I get close enough to them
- As a user, I want some sort of inventory in which I can view my items
### Phase 2 
- As a user, I want to be able to save my current game, including character (inventory, location and name) 
  and map (item locations).
- As a user, I want to be able to resume a game that I have saved.

## **Instructions for Grader**
- After starting up the game you can enter the name of the main character. This name will be what is used to 
save the game. If it is the same name already used it will save over the previous save.
- You can then move the character with a/w/s/d (a is left, w is up, s is down, d is right)
- You can press the inventory button to toggle off and on a window that shows what you are currently
holding. (max of 6 keys in your inventory)
- You can press the pick up item button to pick up any item within reach of the main character
- You can press the drop item button to drop the first item in your inventory
- You can press the add key button to add a key to the game map list of keys on the floor.
- You can press the save game button to save all keys/ inventory/ and character location to a file
with the character name entered
- You can press the load game button to open a save of the name entered in the field that pops up.
If there is no save under that name nothing will occur. 
- Touching a trap will cause you to lose the game.

~~- Dropping 4 keys in the same location will win the game.~~

### Updates to game for phase 4
- To win you now have to have 6 keys in your inventory
- There are 2 flashlights around the map that you can pick up and drop, they do increase your view distance when 
in your inventory

## **Phase 4: Task 2**
For task 2 of phase 4 I made a type hierarchy. I used a abstract class Item that was extended by: Key, Trap, 
and Flashlight (which I fixed and updated to work, it will change view distance and now shows in 
the inventory and can be dropped).

## **Phase 4: Task 3**
My improvements:
- Fix 1: During task 2 I simplified Key and Trap class through the introduction of an abstract class. There was 
some repetitive code, like methods involving being within radius of the object. (this also made it easier to implement 
flashlight as it also extended this class reusing these methods)
- Fix 2: was between GameConsoleInterface and Character classes. Due to how character movement was setup
originally, the movement character method would move him down when sent w and up when sent s. Therefor, this was
corrected in the confusing was swapping the GameConsoleInterface keyhandler sending w to character movement when s was 
pressed instead of correcting the character class.
- Fix 3: Poor cohesion with the GameConsoleInterface. Removed set up and displaying of character name input screen to
its own class as the GameConsoleInterface already does a lot. Everything was updated and moved to the class
CharacterNameInputScreen
- Fix 4: Poor cohesion with the GameConsoleInterface and GameMap. Moved all the generation of the map from the 
GameConsoleInterface to the GameMap itself. (also updated test to make sure they are correct and include tests for 
these new methods) This reduces methods in GameConsoleInterface that really has nothing to do with the interface.
These methods that generate the game map should have been inside the game map itself. 