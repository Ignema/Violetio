# Violetio: Agile Development Project

<p align="center">
<img src="https://raw.githubusercontent.com/anassBenhima/projet-agile-super-mario/main/readme/logo.png" width="200" height="200" />
</p>

# Description

In this project, we were given a sample source code about a mario game developed in java. The objective was to improve this game and work on it through agile sprints to produce a better version.

## New Features

- Added Maven Integration
- Improved Player Collision System
- Designed Original Assets for the Game
- Cleaned up the Source Code
- Coded a Menu for the Game
- Implemented a CI/CD pipeline to build packages automatically
- General Bugfixes

## Game Assets (Not all of them made it into the game)

### Characters

#### Main Player

<p align="center">
<img src="https://raw.githubusercontent.com/anassBenhima/projet-agile-super-mario/main/readme/player.png" width="300" height="auto"  />
</p>

#### Animation

<p align="center">
<img src="https://raw.githubusercontent.com/anassBenhima/projet-agile-super-mario/main/readme/animation.png" />
</p>

<p align="center">
<img src="https://i.imgur.com/HyJs7qw.gif" width="150" height="auto"/>
<img src="https://i.imgur.com/rQB4SJ3.gif" width="150" height="auto"/>
<img src="https://i.imgur.com/axB7O0M.gif" width="150" height="auto"/>
<img src="https://i.imgur.com/r796y8K.gif" width="150" height="auto"/>
</p>

#### Enemies

<p align="center">
<img src="https://raw.githubusercontent.com/anassBenhima/projet-agile-super-mario/main/readme/enemy-01.png" width="200" height="auto" />
<img src="https://raw.githubusercontent.com/anassBenhima/projet-agile-super-mario/main/readme/enemy-02.png" width="200" height="auto" />
<img src="https://raw.githubusercontent.com/anassBenhima/projet-agile-super-mario/main/readme/enemy-03.png" width="200" height="auto" />
<img src="https://raw.githubusercontent.com/anassBenhima/projet-agile-super-mario/main/readme/enemy-04.png" width="200" height="auto" />
</p>

#### Backgrounds

<p align="center">
<img src="https://raw.githubusercontent.com/anassBenhima/projet-agile-super-mario/main/readme/background-01.png" />
</p>

<p align="center">
<img src="https://raw.githubusercontent.com/anassBenhima/projet-agile-super-mario/main/readme/background-02.png" />
</p>

<p align="center">
<img src="https://raw.githubusercontent.com/anassBenhima/projet-agile-super-mario/main/readme/background-03.png" />
</p>

#### Other Assets

<p align="center">
<img src="https://raw.githubusercontent.com/anassBenhima/projet-agile-super-mario/main/readme/star-01.png" width="200" height="200" />
<img src="https://raw.githubusercontent.com/anassBenhima/projet-agile-super-mario/main/readme/star-02.png" width="200" height="200" />
<img src="https://raw.githubusercontent.com/anassBenhima/projet-agile-super-mario/main/readme/star-03.png" width="200" height="200" />
<img src="https://raw.githubusercontent.com/anassBenhima/projet-agile-super-mario/main/readme/star-04.png" width="200" height="200" />
</p>

<p align="center">
<img src="https://raw.githubusercontent.com/anassBenhima/projet-agile-super-mario/main/readme/rock-01.png" width="200" height="auto" />
<img src="https://raw.githubusercontent.com/anassBenhima/projet-agile-super-mario/main/readme/rock-02.png" width="200" height="auto" />
<img src="https://raw.githubusercontent.com/anassBenhima/projet-agile-super-mario/main/readme/rock-03.png" width="200" height="auto" />
</p>

<p align="center">
<img src="https://raw.githubusercontent.com/anassBenhima/projet-agile-super-mario/main/readme/tree.png" />
</p>

## Installation

### Build from source code (recommended)

Clone the repo

    git clone https://github.com/anassBenhima/projet-agile-super-mario.git

Execute **main** located in *src\com\TETOSOFT\tilegame\GameEngine.java* with an IDE

### With Maven

Add this into your *pom.xml* file.

    <dependency>
    <groupId>groupId</groupId>
    <artifactId>projet-agile-super-mario</artifactId>
    <version>1.0-SNAPSHOT</version>
    </dependency>

Install the package

    mvn install

Check your build folder for the jar file and run it

### With JAR

Go to https://github.com/anassBenhima/projet-agile-super-mario/packages and download the jar from the assets on the right

    java -jar projet-agile-super-mario-1.0-<TAG>-jar-with-dependencies.jar