# OpenAuthenticator

### WIP

An authenticator similar to Google Authenticator which syncs to Dropbox and Google Drive


## Blackbox GPG key encryption

The keys to the Fabric apis (the key and secret) are stored in `app/fabric.properties` and encrypted using [Blackbox](https://github.com/StackExchange/blackbox). Please read the documentation!

If you would like to be added as a user in order to change the `fabric.properties` file, follow [these](https://github.com/StackExchange/blackbox#how-to-indoctrinate-a-new-user-into-the-system) instructions.

Essentially, you should:

- Create a GPG key if you had not already
- Add yourself as an admin in the repo
- Add a commit including the pubring, trustdb, and blackbox-admin.txt files

## Notes

- The project uses an MVP Framework, loosely based on ustwo's [Android Boilerplate](https://github.com/ustwo/android-boilerplate)
- It makes heavy use of [RxJava](https://github.com/ReactiveX/RxJava)
- [ZXing](https://github.com/zxing/zxing) is used for QR barcode scanning
    - Apache commons is also used for encoding/decoding
- [Fabric](https://fabric.io/home) is used for beta distributions and analytics

## License

Copyright &copy; 2017 Boris Kachscovsky

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
