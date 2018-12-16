import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ITrainee } from 'app/shared/model/trainee.model';
import { TraineeService } from './trainee.service';
import { ITraining } from 'app/shared/model/training.model';
import { TrainingService } from 'app/entities/training';

@Component({
    selector: 'jhi-trainee-update',
    templateUrl: './trainee-update.component.html'
})
export class TraineeUpdateComponent implements OnInit {
    trainee: ITrainee;
    isSaving: boolean;

    trainings: ITraining[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected traineeService: TraineeService,
        protected trainingService: TrainingService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ trainee }) => {
            this.trainee = trainee;
        });
        this.trainingService.query().subscribe(
            (res: HttpResponse<ITraining[]>) => {
                this.trainings = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.trainee.id !== undefined) {
            this.subscribeToSaveResponse(this.traineeService.update(this.trainee));
        } else {
            this.subscribeToSaveResponse(this.traineeService.create(this.trainee));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITrainee>>) {
        result.subscribe((res: HttpResponse<ITrainee>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackTrainingById(index: number, item: ITraining) {
        return item.id;
    }
}
