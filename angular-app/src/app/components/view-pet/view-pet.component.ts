import { Component, OnInit } from '@angular/core';
import { Pet } from 'src/app/models/pet.model';
import { PetService } from 'src/app/services/pet.service';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
 
@Component({
  selector: 'app-view-pet',
  templateUrl: './view-pet.component.html',
  styleUrls: ['./view-pet.component.css']
})
export class ViewPetComponent implements OnInit {
 
  pets: Pet[] = [];
  userId: number;
  showEditPopup = false;
  showDeletePopup = false;
  selectedPet: Pet | undefined;
 
  constructor(private petService: PetService, private router: Router) { }
 
  ngOnInit(): void {
    this.setUserIdFromLocalStorage();
    this.getAllPetsByUserId();
  }
 
  setUserIdFromLocalStorage(): void {
    const user = JSON.parse(localStorage.getItem('authUser')); // Assuming the user object is stored in local storage
    if (user && user.userId) {
      this.userId = user.userId;
    } else {
      console.error('Error: Invalid user or userId not found');
    }
  }
 
  getAllPetsByUserId() {
    this.petService.getPetsByUserId(this.userId).subscribe((data) => {
      console.log('API response:', data);
      if (Array.isArray(data)) {
        this.pets = data;
      } else {
        console.error('Error: Response is not an array');
      }
    }, (error) => {
      console.error('Error fetching pets:', error);
    });
  }
 
  openEditPopup(pet: Pet) {
    this.selectedPet = pet; // Store the pet object
    this.showEditPopup = true; // Show the popup
  }
 
  editPet() {
    if (this.selectedPet) {
      console.log('Navigating to addpet with petId:', this.selectedPet.petId); // Debugging line
      this.showEditPopup = false; // Close the popup
      this.router.navigate(['/addpet', this.selectedPet.petId]);
    } else {
      console.error('No pet selected for editing');
    }
  }
 
  openDeletePopup(pet: Pet) {
    this.selectedPet = pet; // Store the pet object
    this.showDeletePopup = true; // Show the popup
  }
 
  deletePet() {
    if (this.selectedPet) {
      this.petService.deletePet(this.selectedPet.petId).subscribe(
        (response: any) => {
          console.log(response.message);
          this.ngOnInit();
          console.log("deleted & navigated");
          this.showDeletePopup = false; // Close the popup
        },
        (error: HttpErrorResponse) => {
          if (error.status === 200) {
            const responseBody = JSON.parse(error.error);
            console.log(responseBody.message);
            this.router.navigate(['/viewpet']);
            console.log("deleted & navigated");
            this.showDeletePopup = false; // Close the popup
          } else {
            console.error('Error deleting pet:', error);
          }
        }
      );
    } else {
      console.error('No pet selected for deletion');
    }
  }
}
