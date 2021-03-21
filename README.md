# sipichan-plugin

Plugin for IntelliJ Platform for supporting a Space Application development.

This plugin should help to configure and start developing any Space Application you want.

## Wizard

Plugin provides a several templates and example projects that can help you understand the basics.

There is a list of available and planning templates in the future:
- [x] Blank server template
- [x] Chatbot template with implemented slashcommand and custom ui
- [x] Client template
- [ ] Web-application(backend) template with Authorization Code flow
- [ ] Android template with Refresh Token flow
- [ ] Custom menu templates
- [ ] Slashcommand templates for channels
- [ ] Web-application(frontend) template with Implicit flow
- [ ] More client templates with more detail focusing on Space components


Available authenticate flows to configure:
- [x] Client Credential flow
- [ ] Authentication Code flow
- [ ] Implicit flow
- [ ] Refresh Token flow
- [ ] Resource Owner flow


Available verifying methods to configure:
- [x] Verification token
- [x] Signing key


Available build systems to configure:
- [x] Gradle (groovy)
- [ ] Gradle (kotlin)
- [ ] Maven


## Run configuration 

Another best-to-implement feature for the plugin will be a custom Run Configuration that 
allows starting a tunneling service for more convenient debugging. Here we can choose some different
between ngrok and localtunnel.

## Related repositories

There are several repositories that accumulate templates and solutions, and can be examples as well.

- [Chatbot template](https://github.com/Makentoshe/sipichan-chatbot)
- [Client template](https://github.com/Makentoshe/sipichan-client)