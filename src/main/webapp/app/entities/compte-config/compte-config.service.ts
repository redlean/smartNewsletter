import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { CompteConfig } from './compte-config.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CompteConfigService {

    private resourceUrl =  SERVER_API_URL + 'api/compte-configs';

    constructor(private http: Http) { }

    create(compteConfig: CompteConfig): Observable<CompteConfig> {
        const copy = this.convert(compteConfig);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(compteConfig: CompteConfig): Observable<CompteConfig> {
        const copy = this.convert(compteConfig);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<CompteConfig> {
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
     * Convert a returned JSON object to CompteConfig.
     */
    private convertItemFromServer(json: any): CompteConfig {
        const entity: CompteConfig = Object.assign(new CompteConfig(), json);
        return entity;
    }
    /**
     * Convert a CompteConfig to a JSON which can be sent to the server.
     */
    private convert(compteConfig: CompteConfig): CompteConfig {
        const copy: CompteConfig = Object.assign({}, compteConfig);
        return copy;
    }
}
