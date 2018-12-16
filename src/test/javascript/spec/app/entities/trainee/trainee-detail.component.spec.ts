/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AfpappTestModule } from '../../../test.module';
import { TraineeDetailComponent } from 'app/entities/trainee/trainee-detail.component';
import { Trainee } from 'app/shared/model/trainee.model';

describe('Component Tests', () => {
    describe('Trainee Management Detail Component', () => {
        let comp: TraineeDetailComponent;
        let fixture: ComponentFixture<TraineeDetailComponent>;
        const route = ({ data: of({ trainee: new Trainee(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AfpappTestModule],
                declarations: [TraineeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TraineeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TraineeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.trainee).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
