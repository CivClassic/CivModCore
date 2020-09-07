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

package vg.civcraft.mc.civmodcore.locations.spatial.octrees;

import vg.civcraft.mc.civmodcore.locations.spatial.IIntPoint3D;
import vg.civcraft.mc.civmodcore.locations.spatial.IIntBBox3D;

import java.util.Optional;

/**
 * @author psygate
 */
final class PointOcTreeNode<T extends IIntPoint3D> extends BaseOcTreeNode<PointOcTreeNode<T>, T> {
	PointOcTreeNode(IIntBBox3D bbox, int splitSize) {
		super(bbox, splitSize);
	}

	PointOcTreeNode(IIntBBox3D bbox, int splitSize, PointOcTreeNode parent) {
		super(bbox, splitSize, parent);
	}

	PointOcTreeNode(int minX, int minY, int minZ, int maxX, int maxY, int maxZ, int splitSize, PointOcTreeNode parent) {
		super(minX, minY, minZ, maxX, maxY, maxZ, splitSize, parent);
	}

	@Override
	protected boolean nodeContainsValue(T value) {
		return contains(value);
	}

	@Override
	protected PointOcTreeNode selectChild(T value) {
		assert childrenSize() > 0;
		for (int i = 0; i < childrenSize(); i++) {
			PointOcTreeNode<T> child = getChild(i);
			if (IIntBBox3D.contains(child, value)) {
				return child;
			}
		}

		return null;
	}

	@Override
	protected Optional<PointOcTreeNode<T>> selectChildOpt(T value) {
		PointOcTreeNode node = selectChild(value);

		if (node != null) {
			return Optional.of(node);
		} else {
			return Optional.empty();
		}
	}

	@Override
	protected PointOcTreeNode createNewNode(int minX, int minY, int minZ, int maxX, int maxY, int maxZ, int splitSize, PointOcTreeNode boxOcTreeNode) {
		return new PointOcTreeNode(minX, minY, minZ, maxX, maxY, maxZ, splitSize, this);
	}
}

