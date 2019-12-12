# renote-android (WIP)
[![CodeFactor](https://www.codefactor.io/repository/github/bigman212/renote-android/badge)](https://www.codefactor.io/repository/github/bigman212/renote-android)  
App for keeping and remembering info, using cards.  
[Try it here](https://github.com/bigman212/renote-android/releases)

## Features
- [x] Note manager
- [ ] "Remember mode" - show notes as cards and swipe them to repeat from time to time

## Dev-features
- [x] create domain(model) layer - room db, entities, tables
- [x] create note list view - screen, ViewModel, connect M with VM
- [x] create note creation view - screen, ViewModel, connect M with VM
- [x] implement note addition
- [x] implement note deletion with swipe
- [x] **release v0.2!** (no need for v0.1)
- [x] implement note connected categories preview
- [x] **release v0.3!**
- [ ] polish design
- [ ] "Remember mode"
- [ ] connect websites link to note 
- [ ] create note detailed view - screen, ViewModel, connect M with VM
- [ ] implement note editing

## Dependencies
- Kotlin (*its trending and so perfect! Supports all Java 8 features + functional paradigm*)
- Androidx Navigation Components (*its new and I tried it here to understand pros and cons*) 
- Room + RxJava 2 (*Room is also new, has a lot of potential*) 
- Android Architecture Components (MVVM, LiveData) (*Android officially recommends implementing MVVM architecture and gives us a whole library to do it! Must-try, especially if library promises to remove problems like "keep screen state when config changes"*)  
