# radder
### a play framework ladder rankings webapp 

Radder is a simple [Play!](http://www.playframework.org/) webapp for ladder rankings using the [Elo rating system](http://en.wikipedia.org/wiki/Elo_rating_system).

## installing / running locally

- Install Play! 1.2.4 from [this here address](http://download.playframework.org/releases/play-1.2.4.zip)
- Add it to your PATH variable somewhere
- Clone (or fork) this repository
- Go into the radder directory and run `play dependencies --sync` 
- Now run `play eclipsify` to create some Eclipse project files for you
- Install the Play eclipse plugin by copying the .jar from `/support/eclipse` in your Play installation to `/dropins` in Eclipse
- In Eclipse, import a general existing project located at `/radder`
- Run the app by expanding the eclipse folder, right clicking on radder.launch --> Run As... --> radder
- The app is now running at http://localhost:9000

## NOTE

Master now uses LDAP to authenticate a user. The easiest way to work around this in local development is to set ldapAuthenticated to true in the authentify method in controllers/Security.java after the LDAP check. This will make passwords useless but allow local development.
