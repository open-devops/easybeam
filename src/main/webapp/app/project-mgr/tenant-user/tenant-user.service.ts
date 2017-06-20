import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { TenantUser } from './tenant-user.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class TenantUserService {

    private resourceUrl = 'api/tenant-users';

    constructor(private http: Http) { }

    create(tenantUser: TenantUser): Observable<TenantUser> {
        const copy = this.convert(tenantUser);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(tenantUser: TenantUser): Observable<TenantUser> {
        const copy = this.convert(tenantUser);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<TenantUser> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
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
        return new ResponseWrapper(res.headers, jsonResponse);
    }

    private convert(tenantUser: TenantUser): TenantUser {
        const copy: TenantUser = Object.assign({}, tenantUser);
        return copy;
    }
}
