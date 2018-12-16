/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AfpappTestModule } from '../../../test.module';
import { TraineeUpdateComponent } from 'app/entities/trainee/trainee-update.component';
import { TraineeService } from 'app/entities/trainee/trainee.service';
import { Trainee } from 'app/shared/model/trainee.model';

describe('Component Tests', () => {
    describe('Trainee Management Update Component', () => {
        let comp: TraineeUpdateComponent;
        let fixture: ComponentFixture<TraineeUpdateComponent>;
        let service: TraineeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AfpappTestModule],
                declarations: [TraineeUpdateComponent]
            })
                .overrideTemplate(TraineeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TraineeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TraineeService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Trainee(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.trainee = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Trainee();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.trainee = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
