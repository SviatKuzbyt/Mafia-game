# Mafia-game
Application with the help of which you can play the game in the mafia. Provides handy tools for the court to accompany the role, such as mixing and listing live players and their role.

## Features
- **Gameplay:**
  - The game consists of 3 stages: setting, issuing roles and the game itself
  - **Settings** - here you choose what will be the roles and names of the players
  - **Issuing roles** - at this stage you show players their role (during it, players will not see the role of others)
  - **Players panel** - displayed during the game. It houses all the live players and characters of the role, thanks to chmu easy to follow the game. You can select players, eliminate them and return usunotyh (if you make a mistake)
  - **End** - the application automatically determines the winner and shows information about the game
- **Save the game:** all recent games and settings are saved in a file and can be easily restored
- **Help** - on this tab you can learn the rules of the game in the mafia and also how to use the application
- **Structure:**
  - The program consists of MainActivity where there are 4 (3) buttons on other game activities (creating new and restoring old), help and settings
  - For game activity used fragments for stages
  - I used the recommended architecture in the design, so all activies and fragments work through ViewModel
## Screenshots
 | ![home](https://raw.githubusercontent.com/SviatKuzbyt/Mafia-game/main/screenshots/home.jpg) | ![game](https://github.com/SviatKuzbyt/Mafia-game/blob/main/screenshots/game.gif) |
 | - | - |
 
## How to install?
Go to the latest [Release](https://github.com/SviatKuzbyt/Mafia-game/releases/tag/1.0) and download and install the file `app-release.apk`
