/*
MIT License

Copyright (c) 2020 psygate (psygate.github.com)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package vg.civcraft.mc.civmodcore.locations.spatial.quadtrees;

import vg.civcraft.mc.civmodcore.locations.spatial.IIntBBox2D;
import vg.civcraft.mc.civmodcore.locations.spatial.IIntPoint2D;

import java.util.Optional;

/**
 * @author psygate
 */
final class PointQuadTreeNode<T extends IIntPoint2D> extends BaseQuadTreeNode<PointQuadTreeNode<T>, T> {
	PointQuadTreeNode(IIntBBox2D bbox, int splitSize) {
		super(bbox, splitSize);
	}

	PointQuadTreeNode(IIntBBox2D bbox, int splitSize, PointQuadTreeNode parent) {
		super(bbox, splitSize, parent);
	}

	PointQuadTreeNode(int minX, int minY, int maxX, int maxY, int splitSize, PointQuadTreeNode parent) {
		super(minX, minY, maxX, maxY, splitSize, parent);
	}

	@Override
	protected boolean nodeContainsValue(T value) {
		return contains(value);
	}

	@Override
	protected PointQuadTreeNode selectChild(T value) {
		assert childrenSize() > 0;
		for (int i = 0; i < childrenSize(); i++) {
			PointQuadTreeNode<T> child = getChild(i);
			if (IIntBBox2D.contains(child, value)) {
				return child;
			}
		}

		return null;
	}

	@Override
	protected Optional<PointQuadTreeNode<T>> selectChildOpt(T value) {
		PointQuadTreeNode node = selectChild(value);
		
		if (node != null) {
			return Optional.of(node);
		} else {
			return Optional.empty();
		}
	}

	@Override
	protected PointQuadTreeNode createNewNode(int minX, int minY, int maxX, int maxY, int splitSize, PointQuadTreeNode boxOcTreeNode) {
		return new PointQuadTreeNode(minX, minY, maxX, maxY, splitSize, this);
	}
}

