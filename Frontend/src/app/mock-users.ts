import { User } from './user';

export const USERS: User[] = [
  { type: 'guest', options: [{name: 'Prijava'}, {name: 'Registracija'}]},
  { type: 'admin', options: [{name: 'Uređivanje Servisera'}, {name: 'Pregled Obrazaca'}, {name: 'Settings'}, {name: 'Odjava'}]},
  { type: 'serviser', options: [{name: 'Pregled Obrazaca'}, {name: 'Settings'}, {name: 'Odjava'}]},
  { type: 'korisnik', options: [{name: 'Pregled Obrazaca'}, {name: 'Settings'}, {name: 'Odjava'}]}
];
