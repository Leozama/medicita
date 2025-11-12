//Interfaz de Login
import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, Validators} from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-view-registrar-perfil',
  templateUrl: './view-registrar-perfil.component.html',
  imports: [
    FormsModule
  ],
  styleUrls: ['./view-registrar-perfil.component.css']
})
export class ViewRegistrarPerfilComponent implements OnInit {
  loginForm!: FormGroup;
  submitting = false;
  errorMessage = '';

  constructor(private fb: FormBuilder, private router: Router) {}

  ngOnInit(): void {
    // build reactive form
    this.loginForm = this.fb.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(4)]]
    });
  }

  // getter cómodo
  email: any;
  password: any;
  get f() { return this.loginForm.controls; }

  async onSubmit() {
    this.submitting = true;
    this.errorMessage = '';
    if (this.loginForm.invalid) {
      this.submitting = false;
      return;
    }
    const { username, password } = this.loginForm.value;
    try {
      // Aqui iria la llamada al servicio auth
      // await this.authService.login(username, password);
      console.log('Login con', username, password);
      // ejemplo: navegacion tras login
      this.router.navigate(['/dashboard']);
    } catch (err) {
      this.errorMessage = 'Credenciales incorrectas';
    } finally {
      this.submitting = false;
    }
  }
//Los siguientes métodos son requisito para que el archivo html de registrar perfil funcione, IntelliJ los sugirio
  handleSubmit() {

  }
  switchToRegister() {

  }
}

