import { Injectable } from '@angular/core';
import {USERS} from './mock-users';

@Injectable({
  providedIn: 'root'
})
export class DataService {


  constructor() { }

  getUser() {
      return USERS[0];
  }

  getServices() {
      return [
            {name: 'Prvi', description: 'lorum alkjdaklfbkhgfkaj asjchjkagyakcac'},
            {name: 'Prvi', description: 'lorum alkjdaklfbkhgfkaj asjchjkagyakcac'},
            {name: 'Prvi', description: 'lorum alkjdaklfbkhgfkaj asjchjkagyakcac'},
            {name: 'Prvi', description: 'lorum alkjdaklfbkhgfkaj asjchjkagyakcac'},
            {name: 'Prvi', description: 'lorum alkjdaklfbkhgfkaj asjchjkagyakcac'},
            {name: 'Prvi', description: 'lorum alkjdaklfbkhgfkaj asjchjkagyakcac'},
            {name: 'Prvi', description: 'lorum alkjdaklfbkhgfkaj asjchjkagyakcac'},
            ];
  }
}
