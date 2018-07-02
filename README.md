# Warmind for Destiny 2

An Android companion app for Destiny 2, that utilises the Bungie API to allow players to access and manage their Destiny 2 
characters' inventories, see their stats for various in-game activities and clan functionality (pending API access).

Players can also utilise the LFG functionality to submit a Looking-For-Group post to find other people to play with to fill their
group for Raids/PVP/Weekly activities.

# OAuth

OAuth2 is integrated to allow the app to access the players private character for any active platforms under their Bungie account and their data such as their inventories and transfer/equip items.

![Auth flow](https://media.giphy.com/media/82wISOgzT54f1d4b8c/giphy.gif)

# Item Transfer

Players can access their characters inventories and equip/transfer items between them quickly and efficiently.

![item transfer](https://media.giphy.com/media/35KnJCFyDlsFyhRNx3/giphy.gif)


# LFG Submit

Warmind will dynamically create the appropriate buttons for each character found under the players' account, and some of the selected characters' data will be stored in Firebase so other players can see their stats when viewing other LFG posts details.

![LFG Submission]()
