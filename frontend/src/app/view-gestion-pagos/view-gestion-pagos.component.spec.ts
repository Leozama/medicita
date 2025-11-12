import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewGestionPagosComponent } from './view-gestion-pagos.component';

describe('ViewGestionPagosComponent', () => {
  let component: ViewGestionPagosComponent;
  let fixture: ComponentFixture<ViewGestionPagosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewGestionPagosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewGestionPagosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
