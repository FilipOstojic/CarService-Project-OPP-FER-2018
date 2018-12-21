import { User } from './user';

export const USERS: User[] = [
  { type: 'guest', options: [{name: 'Login'}, {name: 'Sign Up'}]},
  { type: 'admin', options: [{name: 'Uređivanje Servisera'}, {name: 'Pregled Obrazaca'}, {name: 'Settings'}, {name: 'Logout'}]},
  { type: 'serviser', options: [{name: 'Pregled Obrazaca'}, {name: 'Settings'}, {name: 'Logout'}]},
  { type: 'korisnik', options: [{name: 'Pregled Obrazaca'}, {name: 'Settings'}, {name: 'Logout'}]}
];
