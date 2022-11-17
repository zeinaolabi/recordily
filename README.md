<img src="./readme/title1.svg"/>

<div align="center">

> Hello, world! This is the project’s summary that describes the project plain and simple, limited to the space available.   

**[PROJECT PHILOSOPHY](https://github.com/zeinaolabi/recordily_client#-project-philosophy) • [WIREFRAMES](https://github.com/zeinaolabi/recordily_client#-wireframes) • [TECH STACK](https://github.com/zeinaolabi/recordily_client#-tech-stack) • [IMPLEMENTATION](hhttps://github.com/zeinaolabi/recordily_client#-impplementation) • [HOW TO RUN?](https://github.com/zeinaolabi/recordily_client#-how-to-run)**

</div>

<br><br>


<img src="./readme/title2.svg"/>

> Recordily is an application where unknown artist can upload and stream their music. Recordily encourages artists to showcase their talent and show it to the word
by hosting live events for people to enjoy.
> 

### Artist Stories
- As an artist, I want to upload my songs, so people can enjoy them
- As an artist, I want to host live events, to introduce people to my music
- As an artist, I want to record my own songs, to upload them 
- As an artist, I want check my songs statistics, to keep track of my progress 
- As a listener, I want to save songs, so I can check them later

### User Stories
- As a listener, I want to create playlists, so I can customize my music
- As a listener, I want to follow artists, to keep up with the music industry

<br><br>

<img src="./readme/title3.svg"/>

> This design was firstly implememnted on Figma as wireframes, then moved to the mockups to finalize the details.
A Jetpack Library was used for implementing the graphs

| Landing  | Home/Search  |
| -----------------| -----|
| ![Landing]<img src="./readme/landing_page.svg"/> | ![Home/Search]|

| Artists results  | Artist's Albums  |
| -----------------| -----|
| ![Artists results] | ![Artist's Albums]|


<br><br>

<img src="./readme/title4.svg"/>

Here's a brief high-level overview of the tech stack the Well app uses:

**Frontend**
- Android Native application that uses Kotlin as a programming languague, following the MVVM architecture.
- The application follows the latest components:
  - Jetpack Compose
  - Jetpack Navigation
  - Coroutines
**Backend**
- Uses Laravel, which is a PHP framework that follow MVC
- The database is built on MySQL
- Docker image provided
- CI using Github Actions

<br><br>
<img src="./readme/title5.svg"/>

> Using the above mentioned tech stacks and the mockups built with Figma from the user stories we have, the implementation of the app is shown as below, these are screenshots from the real app.

| Landing  | Home/Search  |
| -----------------| -----|
| ![Landing]| ![Home/Search] |


<br><br>
<img src="./readme/title6.svg"/>


> This is an example of how you may give instructions on setting up your project locally.
To get a local copy up and running follow these simple example steps.

### Prerequisites

This is an example of how to list things you need to use the software and how to install them.
* Docker
  ```sh
  install Docker from (https://www.docker.com/)
  ```

### Installation

1. Clone the repo
   ```sh
   git clone https://github.com/zeinaolabi/recordily_client.git
   ```
2. Run Docker
   ```sh
   docker-compose up
   ```
3. Change .env
   ```sh
   update .env according to your variables
   ```
4. Install APK
  ```sh
  install the application's apk from Github Actions
   ```


