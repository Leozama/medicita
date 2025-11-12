import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewGestionPerfilComponent } from './view-gestion-perfil.component';

describe('ViewGestionPerfilComponent', () => {
  let component: ViewGestionPerfilComponent;
  let fixture: ComponentFixture<ViewGestionPerfilComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewGestionPerfilComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewGestionPerfilComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
