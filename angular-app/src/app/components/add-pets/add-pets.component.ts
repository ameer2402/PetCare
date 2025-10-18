import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Pet } from 'src/app/models/pet.model';
import { PetService } from 'src/app/services/pet.service';

@Component({
  selector: 'app-add-pets',
  templateUrl: './add-pets.component.html',
  styleUrls: ['./add-pets.component.css']
})
export class AddPetsComponent implements OnInit {

  pet: Pet = {
    petId: 0,
    name: '',
    species: '',
    breed: '',
    dateOfBirth: '',
    userId: 0,
    status: ''
  };
  petForm: FormGroup;
  isEditing: boolean = false;
  petId: number | null = null;

  constructor(
    private petService: PetService,
    private formBuilder: FormBuilder,
    private activatedRoute: ActivatedRoute,
    private router: Router) {
    this.petForm = this.formBuilder.group({
      name: ['', Validators.required],
      species: ['', Validators.required],
      breed: ['', Validators.required],
      dateOfBirth: ['', [Validators.required, this.noFutureDate]],
      user: this.formBuilder.group({
        userId: ['']
      }),
      status: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    const petIdParam = this.activatedRoute.snapshot.paramMap.get('petId');
    this.petId = petIdParam ? +petIdParam : null;
    console.log('Pet ID from route:', this.petId);

    if (this.petId) {
      this.isEditing = true;
      console.log('Editing Pet ID:', this.petId);
      this.loadPetData(this.petId);
    } else {
      this.setUserIdFromLocalStorage();
    }
  }

  loadPetData(petId: number): void {
    this.petService.findById(petId).subscribe(
      (pet) => {
        console.log('Fetched pet data:', pet);
        this.pet = pet;
        this.petForm.patchValue({
          name: pet.name,
          species: pet.species,
          breed: pet.breed,
          dateOfBirth: this.formatDateForInput(pet.dateOfBirth), // Format the date
          user: {
            userId: pet.userId
          },
          status: pet.status
        });
      },
      (error) => {
        console.error('Error fetching pet data:', error);
      }
    );
  }

  setUserIdFromLocalStorage(): void {
    const user = JSON.parse(localStorage.getItem('authUser'));
    if (user && user.userId) {
      this.pet.userId = user.userId;
      this.petForm.patchValue({
        user: {
          userId: user.userId
        }
      });
    }
  }

  noFutureDate(control: AbstractControl): ValidationErrors | null {
    const currentDate = new Date();
    const selectedDate = new Date(control.value);
    if (selectedDate > currentDate) {
      return { 'futureDate': true };
    }
    return null;
  }

  formatDateForInput(date: any): string {
    if (!date) {
      return '';
    }

    if (Array.isArray(date)) {
      const [year, month, day] = date;
      return `${year}-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}`;
    }

    const dateTimeString = date.split(' ')[0];
    const parsedDate = new Date(dateTimeString);
    const year = parsedDate.getFullYear();
    const month = (parsedDate.getMonth() + 1).toString().padStart(2, '0');
    const day = parsedDate.getDate().toString().padStart(2, '0');
    return `${year}-${month}-${day}`;
  }

  formatToLocalDateTime(date: string): string {
    return `${date}T00:00:00`;
  }

  addPet(): void {
    console.log("addpet method");
    if (this.petForm.valid) {
      console.log("valid");
      const formData = {
        ...this.petForm.value,
        dateOfBirth: this.formatToLocalDateTime(this.petForm.value.dateOfBirth)
      };

      console.log('Form Data:', formData);

      if (this.isEditing) {
        const user = JSON.parse(localStorage.getItem('authUser'));
        console.log(user);
        console.log(user.userId);

        this.pet = {
          ...formData,
          user:{
            userId: user.userId
          }
        }
        // console.log("................pet......."+this.pet);
        
        this.petService.updatePet(this.petId,this.pet).subscribe(
          () => {
            console.log('Pet updated successfully');
            this.router.navigate(['/viewpet']);
          },
          (error) => console.error('Error updating pet:', error)
        );
      } else {
        this.petService.addPet({ ...this.pet, ...formData }).subscribe(
          () => {
            console.log('Pet added successfully');
            this.router.navigate(['/viewpet']);
          },
          (error) => console.error('Error adding pet:', error)
        );
      }
    } else {
      console.log('Form is invalid:', this.petForm.value);
    }
  }
}