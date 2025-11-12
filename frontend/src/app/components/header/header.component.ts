import { Component, OnInit } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService, User } from '../../services/auth.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterLink, CommonModule],
  templateUrl: './header.component.html',
  
})
export class HeaderComponent implements OnInit {
  showUserSidebar = false;
  currentUser: User | null = null;
  isLoggedIn = false;
  
  constructor(
      private authService: AuthService,
      private router: Router // ✅ Agregar esta línea
    ) {}

  ngOnInit() {
    this.authService.currentUser$.subscribe(user => {
      this.currentUser = user;
      this.isLoggedIn = !!user;
    });
  }

  getIniciales(): string {
    if (this.currentUser?.nombre) {
      return this.currentUser.nombre
        .split(' ')
        .filter((_, index) => index === 0 || index === 1)
        .map(nombre => nombre[0])
        .join('')
        .toUpperCase();
    }
    return 'US';
  }

  openUserSidebar() {
    this.showUserSidebar = true;
  }

  closeUserSidebar() {
    this.showUserSidebar = false;
  }

  logout() {
    this.authService.logout();
    this.closeUserSidebar();
  }

  navigateToDashboard() {
  this.router.navigate(['/dashboard']);
  this.closeUserSidebar();
  }

  irALogin() {
  this.router.navigate(['/login']);
  }

  isLoginPage(): boolean {
    return this.router.url === '/login' || this.router.url === '/auth/login';
  }
  
}