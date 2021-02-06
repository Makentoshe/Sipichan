# sipichan-plugin

Plugin for IntelliJ Platform for supporting a Space Application development.

This plugin should help to configure and start developing any Space Application you want.

It has several initial projects that can help you understand the basics.

## Progress list

- [ ] Wizard
    - [x] Initial step: template choosing
    - [ ] Space step: helps to register application and configure tokens, api, etc. - in progress
    - [ ] Build step: helps to configure build system
- [ ] Templates: repeats [most of the tutorials](https://www.jetbrains.com/help/space/applications.html#types-of-applications)
    - [x] Blank project
    - [x] Echo chatbot project
    - [ ] Slash command chatbot project
    - [ ] Custom menus
    - [ ] Client application
- [ ] Run configuration
    - [x] Running single ktor application - just use default kotlin run configuration
    - [ ] Running ktor application with tunneling service
        - [ ] Localtunnel - in progress
        - [ ] Ngrok - frozen (requires payments)
    