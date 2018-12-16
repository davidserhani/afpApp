import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITrainee } from 'app/shared/model/trainee.model';

type EntityResponseType = HttpResponse<ITrainee>;
type EntityArrayResponseType = HttpResponse<ITrainee[]>;

@Injectable({ providedIn: 'root' })
export class TraineeService {
    public resourceUrl = SERVER_API_URL + 'api/trainees';

    constructor(protected http: HttpClient) {}

    create(trainee: ITrainee): Observable<EntityResponseType> {
        return this.http.post<ITrainee>(this.resourceUrl, trainee, { observe: 'response' });
    }

    update(trainee: ITrainee): Observable<EntityResponseType> {
        return this.http.put<ITrainee>(this.resourceUrl, trainee, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITrainee>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITrainee[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
