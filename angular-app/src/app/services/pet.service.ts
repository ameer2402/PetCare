
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Pet } from '../models/pet.model';
import { User } from '../models/user.model';
import { tap } from 'rxjs/operators';
 
@Injectable({
  providedIn: 'root'
})
export class PetService {

  public readonly backendUrl = 'https://8080-fadffcfdbddbdfbdfacfcddfbedbebb.premiumproject.examly.io/api/pet';

  constructor(private readonly http: HttpClient) { }

  addPet(pet: Pet): Observable<Pet> {
    console.log("serive",pet);
    return this.http.post<Pet>(this.backendUrl, pet);
  }

  getAllPets(): Observable<Pet[]>{
    return this.http.get<Pet[]>(this.backendUrl);
  }

  getPetsByIds(petIds: number[]): Observable<Pet[]> {
    // Implement the API call to fetch pets by their IDs
    return this.http.post<Pet[]>('/api/pets/by-ids', { petIds });
  }
  

  updatePet(petId:number,pet: Pet): Observable<Pet> {
    console.log("updating pet",pet);
    console.log("updating pet",pet.petId);
    return this.http.put<Pet>(`${this.backendUrl}/${petId}`, pet);
  }

  deletePet(petId: number): Observable <Pet> {
    return this.http.delete<Pet>(`${this.backendUrl}/${petId}`);
  }

  getPetsByUserId(userId: number): Observable<Pet[]> {
    return this.http.get<Pet[]>(`${this.backendUrl}/user/${userId}`).pipe(
      tap((response) => console.log('getPetsByUserId response:', response))
    );
  }
  

  findById(petId: number): Observable<Pet> {
    return this.http.get<Pet>(`${this.backendUrl}/${petId}`);
  }

}