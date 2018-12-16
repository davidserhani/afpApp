import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Trainee } from 'app/shared/model/trainee.model';
import { TraineeService } from './trainee.service';
import { TraineeComponent } from './trainee.component';
import { TraineeDetailComponent } from './trainee-detail.component';
import { TraineeUpdateComponent } from './trainee-update.component';
import { TraineeDeletePopupComponent } from './trainee-delete-dialog.component';
import { ITrainee } from 'app/shared/model/trainee.model';

@Injectable({ providedIn: 'root' })
export class TraineeResolve implements Resolve<ITrainee> {
    constructor(private service: TraineeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Trainee> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Trainee>) => response.ok),
                map((trainee: HttpResponse<Trainee>) => trainee.body)
            );
        }
        return of(new Trainee());
    }
}

export const traineeRoute: Routes = [
    {
        path: 'trainee',
        component: TraineeComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'afpappApp.trainee.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'trainee/:id/view',
        component: TraineeDetailComponent,
        resolve: {
            trainee: TraineeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'afpappApp.trainee.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'trainee/new',
        component: TraineeUpdateComponent,
        resolve: {
            trainee: TraineeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'afpappApp.trainee.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'trainee/:id/edit',
        component: TraineeUpdateComponent,
        resolve: {
            trainee: TraineeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'afpappApp.trainee.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const traineePopupRoute: Routes = [
    {
        path: 'trainee/:id/delete',
        component: TraineeDeletePopupComponent,
        resolve: {
            trainee: TraineeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'afpappApp.trainee.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
