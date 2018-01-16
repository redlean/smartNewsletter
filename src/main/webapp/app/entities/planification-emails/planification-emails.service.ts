import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { PlanificationEmails } from './planification-emails.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PlanificationEmailsService {

    private resourceUrl = SERVER_API_URL + 'api/planification-emails';

    constructor(private http: Http) { }

    create(planificationEmails: PlanificationEmails): Observable<PlanificationEmails> {
        const copy = this.convert(planificationEmails);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }
    sendTo(planificationEmails: PlanificationEmails): Observable<PlanificationEmails> {
        const copy = this.convert(planificationEmails);
        return this.http.post(this.resourceUrl + '/send', copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(planificationEmails: PlanificationEmails): Observable<PlanificationEmails> {
        const copy = this.convert(planificationEmails);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<PlanificationEmails> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to PlanificationEmails.
     */
    private convertItemFromServer(json: any): PlanificationEmails {
        const entity: PlanificationEmails = Object.assign(new PlanificationEmails(), json);
        return entity;
    }

    /**
     * Convert a PlanificationEmails to a JSON which can be sent to the server.
     */
    private convert(planificationEmails: PlanificationEmails): PlanificationEmails {
        const copy: PlanificationEmails = Object.assign({}, planificationEmails);
        return copy;
    }
}
