import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ITrainee } from 'app/shared/model/trainee.model';
import { TraineeService } from './trainee.service';

@Component({
    selector: 'jhi-trainee-update',
    templateUrl: './trainee-update.component.html'
})
export class TraineeUpdateComponent implements OnInit {
    trainee: ITrainee;
    isSaving: boolean;

    constructor(protected traineeService: TraineeService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ trainee }) => {
            this.trainee = trainee;
        });
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
}
