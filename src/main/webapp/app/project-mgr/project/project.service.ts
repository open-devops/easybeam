import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { LocalStorageService } from 'ng2-webstorage';

import { Project } from './project.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ProjectService {

    private resourceUrl = 'api/projects';

    constructor(private http: Http,
                private $storageService: LocalStorageService) {
    }

    setCurrentProject(project: Project) {
        this.$storageService.store('currentProject', project);
    }

    getCurrentProject(): Project {
        return this.$storageService.retrieve('currentProject');
    }

    create(project: Project): Observable<Project> {
        const copy = this.convert(project);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(project: Project): Observable<Project> {
        const copy = this.convert(project);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Project> {
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

    private convert(project: Project): Project {
        const copy: Project = Object.assign({}, project);
        return copy;
    }
}
