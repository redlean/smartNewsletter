import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Planification_emails } from './planification-emails.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class Planification_emailsService {

    private resourceUrl =  SERVER_API_URL + 'api/planification-emails';

    constructor(private http: Http) { }

    create(planification_emails: Planification_emails): Observable<Planification_emails> {
        const copy = this.convert(planification_emails);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(planification_emails: Planification_emails): Observable<Planification_emails> {
        const copy = this.convert(planification_emails);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Planification_emails> {
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
     * Convert a returned JSON object to Planification_emails.
     */
    private convertItemFromServer(json: any): Planification_emails {
        const entity: Planification_emails = Object.assign(new Planification_emails(), json);
        return entity;
    }

    /**
     * Convert a Planification_emails to a JSON which can be sent to the server.
     */
    private convert(planification_emails: Planification_emails): Planification_emails {
        const copy: Planification_emails = Object.assign({}, planification_emails);
        return copy;
    }
}
