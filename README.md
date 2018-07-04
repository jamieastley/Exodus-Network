# Warmind for Destiny 2 (WIP)

An Android companion app for Destiny 2, that utilises the Bungie API to allow players to access and manage their Destiny 2 
characters' inventories, see their account-wide stats for various in-game activities, weekly milestones and what Xur is selling this week along with his current location.

Players can also utilise the LFG functionality to submit a Looking-For-Group post to find other people to play with to fill their
group for Raids/PVP/Weekly activities.

In progress: currently working on refactoring app to utilise Android Arch components and Dagger 2.

# OAuth

OAuth2 is integrated to allow the app to access the players private character for any active platforms under their Bungie account and their data such as their inventories and transfer/equip items.

![Auth flow](https://media.giphy.com/media/82wISOgzT54f1d4b8c/giphy.gif)

# Item Transfer

Players can access their characters inventories and equip/transfer items between them quickly and efficiently.

![item transfer](https://media.giphy.com/media/35KnJCFyDlsFyhRNx3/giphy.gif)


# LFG Submit

Warmind will dynamically create the appropriate buttons for each character found under the players' account, and some of the selected characters' data will be stored in Firebase so other players can see their stats when viewing other LFG posts details.

![lfg Submission](https://media.giphy.com/media/1xlKJ8SvD5H64Mjcct/giphy.gif)

# Account-wide Stats

Overall statistics for all characters under the currently logged-in platform.

![Account stats](https://media.giphy.com/media/PgQCt8IGWSYHQfQDJ2/giphy.gif)

# Weekly Milestones

Quickly see what milestones are active this week and what rewards they're offering.

![Milestones](https://media.giphy.com/media/fBGdWca5QVgF3TO6Ft/giphy.gif)

# Xur

Tired of looking for where Xur is this week? Wander no more as Warmind will grab his current location and stock list, along with sale history count courtesy of www.braytech.org/api.

![Xur](https://media.giphy.com/media/WNc9QbEO1GEHzIWT0j/giphy.gif)
